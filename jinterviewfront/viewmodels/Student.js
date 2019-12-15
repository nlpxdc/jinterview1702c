var app = new Vue({
    el: '#app',
    data:{
            studentId:'',
            realname:'',
            email:'',
            mobile:'',
            avatarUrl:''

    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.studentId = url.searchParams.get("studentId");
        this.getstudent();
    },
    methods: {
        getstudent(studentId) {
            axios.get("http://localhost:8080/student/getBasicInfo", {
                params: {
                    studentId: this.studentId
                }
            })
                .then(response => {
                    console.log(response);
                    var student = response.data
                    this.avatarUrl = student.avatarUrl;
                    this.mobile = student.mobile;
                    this.email = student.email;
                    this.realname = student.realname;
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    }
    
});