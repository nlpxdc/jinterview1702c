var app = new Vue({
    el: '#app',
    data: {
        interviewId: '',
        examPhotos: []
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
                    alert('提交成功');
                })
                .catch(function (error) {
                    console.log(error);
                    alert(error.response.data.message);
                });
        }
    }
});