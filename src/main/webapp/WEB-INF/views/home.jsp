<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/org/cometd.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>

<script type="text/javascript">
var config = {
	contextPath: '${pageContext.request.contextPath}'
};
(function($) {
	var cometd = $.cometd;

	$(document).ready(function() {
		function _connectionEstablished() {
			console.log('Connection Established');
		}

		function _connectionBroken() {
			console.log('Connection Broken');
		}

		function _connectionClosed() {
			console.log('Connection Closed');
		}

		// Function that manages the connection status with the Bayeux server
		var _connected = false;
		function _metaConnect(message) {
			if (cometd.isDisconnected()) {
				_connected = false;
				_connectionClosed();
				return;
			}

			var wasConnected = _connected;
			_connected = message.successful === true;
			if (!wasConnected && _connected) {
				_connectionEstablished();
			} else if (wasConnected && !_connected) {
				_connectionBroken();
			}
		}

		// Function invoked when first contacting the server and
		// when the server has lost the state of this client
		function _metaHandshake(handshake) {
			if (handshake.successful === true) {
				cometd.batch(function() {
					
					cometd.subscribe('/newGame', function(message) {
						var game = message.data;
						$('#games').append($('<li></li>').text(game.id + ' started by ' + game.owner.name).data('game-id', game.id).prepend($('<a>Join</a> ').attr('href', '${pageContext.request.contextPath}/app/Game/' + game.id + '/join')))
					});
					
				});
			}
		}

		// Disconnect when the page unloads
		$(window).unload(function() {
			cometd.disconnect(true);
		});

		var cometURL = location.protocol + "//" + location.host + config.contextPath + "/cometd";
		cometd.configure({
			url: cometURL
		});

		cometd.addListener('/meta/handshake', _metaHandshake);
		cometd.addListener('/meta/connect', _metaConnect);

		cometd.handshake();
	});
})(jQuery);
</script>
</head>
<body>
	<c:choose>
		<c:when test="${player ne null}">
			<div>
				Player: ${player.name}
			</div>
			<div>
				Create New Game
				<form action="${pageContext.request.contextPath}/app/Game/new" method="GET">
					<button type="submit">Create Game</button>
				</form>
			</div>
			<div>
				Games
				<ul id="games">
					<c:forEach var="game" items="${games}">
						<li data-game-id="${game.id}"><a href="${pageContext.request.contextPath}/app/Game/${game.id}/join">Join</a> ${game.id} started by ${game.owner.name}</li>
					</c:forEach>
				</ul>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				Create Account
				<form action="${pageContext.request.contextPath}/app/Player/new" method="GET">
					<div>
						<label for="accountName">Name</label>
						<input id="name" name="name" type="text" />
					</div>
					<button type="submit">Create Account</button>
				</form>
			</div>
		</c:otherwise>
	</c:choose>
	
</body>
</html>
