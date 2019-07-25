var currencyRevenueChart = null;
var clientRevenueChart = null;
var itemPaymentRefundChart = null;
var arr = [];
var loginCount = 0;
var userCount = 0;

function connect() {
    socket = new SockJS('/dashboard');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/userCount', function (message) {
            updateUserCount(message);
        });
        stompClient.subscribe('/topic/loginCount', function (message) {
            updateLoginCount(message);
        });
        stompClient.subscribe('/topic/fraud', function (message) {
            updateFraud(message);
        });
        stompClient.subscribe('/topic/currencyRevenue', function (message) {
            updateCurrencyRevenue(message);
        });
        stompClient.subscribe('/topic/clientRevenue', function (message) {
            updateClientRevenue(message);
        });
        stompClient.subscribe('/topic/itemPaymentRefund', function (message) {
            updateItemPaymentRefund(message);
        });

        $.get("/initializeData");
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}

function updateUserCount(message) {
    userCount += +message.body;
    console.log(message.body);
    $('#total-customer-error').html(userCount);
}

function updateLoginCount(message) {
    loginCount+=+message.body;
    console.log(message.body);
    $('#total-customerCount').html(loginCount);
}

function updateFraud(message) {
    var fraud = $.parseJSON(message.body);
    console.log(fraud);
    new TabbedNotification({
        title: fraud.title,
        text: 'Account Number: ' + fraud.accountNumber + '\nReason: ' + fraud.fraudReason,
        type: 'error',
        sound: true
    });
}

function updateCurrencyRevenue(message) {
    currencyRevenueChart.load({
        json: $.parseJSON(message.body)
    });
}

function updateClientRevenue(message) {
    clientRevenueChart.load({
        json: $.parseJSON(message.body)
    });
}

function updateItemPaymentRefund(message) {
    itemPaymentRefundChart.load({
        json: $.parseJSON(message.body),
        keys: {
            x: 'item',
            value: ['PAYMENT', 'REFUND']
        }
    });
}

$(document).ready(function () {
    connect();

    currencyRevenueChart = c3.generate({
        bindto: '#currency-revenue',
        data: {
            type: 'pie',
            json: {}
        }
    });

    clientRevenueChart = c3.generate({
        bindto: '#client-revenue',
        tooltip: {
            format: {
                value: function (x) {
                    return '$' + x;
                }
            }
        },
        data: {
            type: 'donut',
            json: {}
        }
    });

    itemPaymentRefundChart = c3.generate({
        bindto: '#item-payment-refund',
        data: {
            type: 'bar',
            json: {}
        },
        axis: {
            x: {
                type: 'category'
            }
        }
    });
});
