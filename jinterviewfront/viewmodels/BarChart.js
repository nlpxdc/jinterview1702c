var app = new Vue({
    el: '#app',
    data: {
    
     
     
        
        counts: {},
        labels: [],
        data: [],
        base: "",
        active:0,
    },

    methods: {
        onClickLeft() {

        },
        onClickRight() {

        },
        getInterviewCount() {
            axios.get("/chart/interviewCount").then(res => {
                this.counts = res.data;
                console.log(this.counts);
                for (var key in this.counts) {
                    this.data.push(this.counts[key]);
                    this.labels.push(key);
                };
                console.log(this.data);
                console.log(this.labels);
                var myChart = echarts.init(document.getElementById('myChart'));
                var option = {
                    bezierCurve: false,
                    animation: false,
                    color: ['#3398DB'],
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: this.labels,
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '直接访问',
                            type: 'bar',
                            barWidth: '60%',
                            data: this.data
                        }
                    ]
                };
                myChart.setOption(option);
                var imgData = myChart.getDataURL();
                this.base = imgData;
                this.tj();


                // var chart = new Chart(ctx, {
                //     // The type of chart we want to create
                //     type: 'bar',

                //     // The data for our dataset
                //     data: {
                //         labels:this.labels,
                //         datasets: [{
                //             label: '面试次数',
                //             backgroundColor: 'rgb(255, 99, 132)',
                //             borderColor: 'rgb(255, 99, 132)',
                //             data: this.data,
                //         }],
                //     },

                //     // Configuration options go here
                //     options: {
                //         scales:{
                //             yAxes:[{
                //                 ticks:{
                //                     beginAtZero:true,//从0开始
                //                     stepSize:2//刻度
                //                 }
                //             }]
                //         }
                //     }



                // });

            });

        },
        tj() {
            axios.post('/chart/saveword', this.base).
                then(function (response) {
                    console.log(response);
                }).catch(function () {
                    console.log('FAILURE!!');
                });

        },
        pdf() {
            axios(
                {
                    baseURL: "/jinterviewback",

                    url: "/chart/downpdf",
                    method: "get",
                    responseType: 'blob',
                    header: {
                        'jinterviewToken': localStorage['jinterviewToken']
                    }
                })
                .then(res => {

                    console.log(res)
                    const fileName = '面试次数图.pdf';


                    console.log("response: ", res);
                    // new Blob([data])用来创建URL的file对象或者blob对象
                    let url = window.URL.createObjectURL(new Blob([res.data]));
                    // 生成一个a标签
                    let link = document.createElement("a");
                    link.style.display = "none";
                    link.href = url;
                    // 生成时间戳
                    let timestamp = new Date().getTime();
                    link.download = timestamp + ".pdf";
                    document.body.appendChild(link);
                    link.click();


                })
                .catch(err => {
                    console.error(err);
                })
        },
        word() {
            axios(
                {
                    baseURL: "/jinterviewback",
                    url: "/chart/downword",
                    method: "get",
                    responseType: 'blob',
                    header: {
                        'jinterviewToken':localStorage['jinterviewToken']
                    }
                })
                .then(res => {

                    // console.log("response: ", res);
                    // // new Blob([data])用来创建URL的file对象或者blob对象
                    // let url = window.URL.createObjectURL(new Blob([res.data]));
                    // // 生成一个a标签
                    // let link = document.createElement("a");
                    // link.style.display = "none";
                    // link.href = url;
                    // // 生成时间戳
                    // let timestamp = new Date().getTime();
                    // link.download = timestamp + ".doc";
                    // document.body.appendChild(link);
                    // link.click();


                    let url = window.URL.createObjectURL(new Blob([res.data]));
                    let link = document.createElement('a');
                    link.style.display = 'none';
                    link.href = url;
                    link.setAttribute('download', '测试.doc');
             
                    document.body.appendChild(link);
                    link.click()
          


                })
                .catch(err => {
                    console.error(err);
                })
        },
    },
    mounted() {
        // this.getStudentInfo();
        this.getInterviewCount();
    }
});