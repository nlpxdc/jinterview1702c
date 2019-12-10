var app = new Vue({
    el: '#app',
    data() {
    return {
    student: {}
    
    };
    },
    methods:{
    getstudent(studentId){
    axios.get("http://localhost:8080/student/getBasicInfo",{params:{studentId:studentId}})
    .then(res=>{
    this.student = res.data;
    })
    .catch(err =>{
    console.error(err);
    })
    }
    },
    mounted(){
    this.getstudent(1);
    }
    });