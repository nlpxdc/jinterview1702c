var app = new Vue({
    el: '#app',
    data: {
        realname:'',
        labels:[],
    },

    methods:{
        getStudentInfo(){
            axios.get("http://localhost:80/chart/student").then(res=>{
                this.realname = res.data;
                console.log(this.realname);
                this.realname.forEach(item=>{
                    console.log(item.realname)
                    this.labels.push(item.realname);
                })
                this.labels.forEach(item=>{
                    console.log(item)
                })
                var ctx = document.getElementById('myChart').getContext('2d');
                var chart = new Chart(ctx, {
                // The type of chart we want to create
                 type: 'bar',

                // The data for our dataset
                data: {
                    labels:this.labels,
                    datasets: [{
                        label: 'Interview dataset',
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: [3,10,5,2,20,30,45,23,32,15,7,31]
                    }]
                },

                // Configuration options go here
                options: {
                    
                }
                });
            });
        }
    },
    mounted(){
        this.getStudentInfo();
            
        }
});