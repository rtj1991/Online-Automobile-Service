$(function () {
    $("#start").datepicker({dateFormat: 'yy-mm-dd'});
    $("#end").datepicker({dateFormat: 'yy-mm-dd'});
});

var dataArray = [];

function pushDataToChart(k, v, t) {
    var p = v / t * 100;
    dataArray.push({name: k, y: p, drilldown: null});
}

$(function () {
    // Create the chart
    $('#chart').highcharts({
        chart: {
            type: 'pie'
        },
        title: {
            text: 'Collection Summary'
        },
        plotOptions: {
            series: {
                dataLabels: {
                    enabled: true,
                    format: '{point.name}: {point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },
        series: [{
            name: 'Payment method',
            colorByPoint: true,
            data: dataArray
        }],
        drilldown: {}
    });
});

