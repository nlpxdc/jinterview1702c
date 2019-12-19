var app = new Vue({
    el: '#app',
    data: {
        interviewId: '',
        examPhotos: [],
        Photoloading:false,
    },
    mounted() {
        console.log('view mounted');

        var url = new URL(location.href);
        this.interviewId = url.searchParams.get("interviewId");
        if (!this.interviewId) {
            alert('interviewId 不存在');
            return;
        }
    },
    methods: {
        handleSubmitUploadClick() {
            console.log('submit upload click');
            this.Photoloading=true;
            this.submitUpload();
        },
        submitUpload() {
            var formData = new FormData();
            this.examPhotos.forEach(file => {
                formData.append('examphotos', file.file);
            });
            formData.append('interviewId', this.interviewId);
            axios.post('/examphoto/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.Photoloading=false;
                    alert('提交成功');
                    history.back();
                })
                .catch(function (error) {
                    app.Photoloading=false;

                    console.log(error);
                    alert(error.response.data.message);
                    app.Photoloading=false;
                });
        }
    }
});