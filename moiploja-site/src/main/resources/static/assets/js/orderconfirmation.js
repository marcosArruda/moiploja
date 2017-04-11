var stompClient = null;
function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/pay/' + order.paymentId, function (result) {
            if (result.body === "AUTHORIZED") {
                $("status_pedido").val("Compra autorizada e realizada com sucesso!");
                stompClient.unsubscribe();
                stompClient.disconnect();
            }
        });
    });
}

$(function () {
    connect();
});