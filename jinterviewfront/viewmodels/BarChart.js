var app = new Vue({
    el: '#app',
    data: {
        counts:{},
        labels:[],
        data:[],
    },

    methods:{
        // getStudentInfo(){
        //     axios.get("http://localhost:8080/chart/student").then(res=>{
        //         this.realname = res.data;
        //         console.log(this.realname);
        //         this.realname.forEach(item=>{
        //             //console.log(item.realname)
        //             this.labels.push(item.realname);
        //         })
        //     });
        // },
        onClickLeft(){

        },
        onClickRight(){

        },
        getInterviewCount(){
            axios.get("http://localhost:80/chart/interviewCount").then(res=>{
                this.counts=res.data;
            console.log(this.counts);
            for(var key in this.counts){
                this.data.push(this.counts[key]);
                this.labels.push(key);
            };
            console.log(this.data);
            console.log(this.labels);
            var ctx = document.getElementById('myChart').getContext('2d');
            var chart = new Chart(ctx, {
                // The type of chart we want to create
                type: 'bar',

                // The data for our dataset
                data: {
                    labels:this.labels,
                    datasets: [{
                        label: '面试次数',
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: this.data,
                    }],
                },

                // Configuration options go here
                options: {
                    scales:{
                        yAxes:[{
                            ticks:{
                                beginAtZero:true,//从0开始
                                stepSize:1//刻度
                            }
                        }]
                    }
                }
            });
        });

        }
    },
    mounted(){
        // this.getStudentInfo();
        this.getInterviewCount();
    }
});