var currencyRevenueChart = null;
var clientRevenueChart = null;
var itemPaymentRefundChart = null;
var arr = [];
var loginCount = 0;
var userCount = 0;
var downloadCount = 0;
var errorCount = 0;

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

        stompClient.subscribe('/topic/errorCount', function (message) {
            updateErrorCount(message);
        });

        stompClient.subscribe('/topic/downloadCount', function (message) {
            updateDownloadCount(message);
        });

        stompClient.subscribe('/topic/segmentCount', function (message) {
            console.log('Test : ' + message.body);
            updateSegmentCount(message);
        });
        stompClient.subscribe('/topic/usageCount', function (message) {
            updateUsageCount(message);
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

function updateErrorCount(message) {
    errorCount+=+message.body;
    console.log(message.body);
    $('#total-errorCount').html(errorCount);
}

function updateDownloadCount(message) {
    downloadCount+=+message.body;
    console.log(message.body);
    $('#total-downloadCount').html(downloadCount);
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

function updateSegmentCount(message) {
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


function updateUsageCount(message){

    var featureLabels = [];
    var featurecounts = [];

    var messageBody = $.parseJSON(message.body);

    console.log(messageBody);

    featureLabels = Object.keys(messageBody);

    console.log(featurecounts);


    featureLabels.map( f => {featurecounts.push(messageBody[f])});

    console.log('Hi' + featurecounts);

    if ($('#mybarChart').length ){

        var ctx = document.getElementById("mybarChart");
        var mybarChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: featureLabels,
                datasets: [{
                    label: '# of clicks',
                    backgroundColor: "#26B99A",
                    data: featurecounts
                }]
            },

            options: {
                responsive:true,
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });

    }
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
