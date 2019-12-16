var app = new Vue({
    el: '#app',
    data: {
        urls: [],
        files: [],
        interviewId: '',
    },
    methods: {
        afterRead(file) {
            console.log('after read');
            console.log(file);
            console.log(this.files);
        },
        upload() {
            var formData = new FormData();

            this.files.forEach(file => {
                formData.append("audiorecords", file.file);
            });
            formData.append("interviewId", this.interviewId);
            axios.post('/audiorecord/upload', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then(function(response) {
                    console.log(response);
                    this.urls = response.data;
                })
                .catch(function(error) {
                    console.log(error);
                });
        },
        handleUpload() {
            console.log('upload click');

            if (!this.files.length) {
                alert('least one file');
                return;
            }

            this.upload();
        }
    },
    mounted() {

    },
});