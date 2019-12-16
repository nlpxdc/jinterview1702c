var app = new Vue({
    el: '#app',
    data: {
        studentId: '',
        nickname: '',
        realname: '',
        email: '',
        mobile: '',
        avatarUrl: ''

    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.studentId = url.searchParams.get("studentId");
        if (!this.studentId) {
            alert('studentId 不存在');
            return;
        }

        this.getstudent();
    },
    methods: {
        getstudent() {
            axios.get("/student/getBasicInfo", {
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
                    this.nickname = student.nickname;
                    this.realname = student.realname;
                })
                .catch(function (error) {
                    console.error(error);
                });
        }
    }

});