var app = new Vue({
    el: '#app',
    data: {
        interviewId: '',
        examPhotos: [],
        uploadExamPhotos: [],
        photoLoading: false
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
            if (!this.examPhotos.length) {
                alert('请至少选择一张图');
                return;
            }
            this.submitUpload();
        },
        submitUpload() {
            var formData = new FormData();
            this.uploadExamPhotos.forEach(file => {
                formData.append('examphotos', file);
            });
            formData.append('interviewId', this.interviewId);
            this.photoLoading = true;
            axios.post('/examphoto/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.photoLoading = false;
                    alert('提交成功');
                    history.back();
                })
                .catch(function (error) {
                    app.photoLoading = false;

                    console.log(error);
                    alert(error.response.data.message);
                    app.photoLoading = false;
                });
        },
        beforeRead(file) {
            if (Array.isArray(file)) {
                const files = file;
                for (let i = 0; i < files.length; i++) {
                    const sfile = files[i];
                    if (sfile.type !== 'image/jpeg') {
                        alert('请上传 jpg 格式图片');
                        return false;
                    }
                }
            } else {
                if (file.type !== 'image/jpeg') {
                    alert('请上传 jpg 格式图片');
                    return false;
                }
            }

            return true;
        },
        afterRead() {
            this.uploadExamPhotos = [];
            this.examPhotos.forEach(examphoto => {
                var img = new Image();
                img.onload = function () {
                    const width = img.naturalWidth;
                    const height = img.naturalHeight;
                    const pixel = 720;

                    if (width < height) {
                        if (width > pixel) {
                            var canvas = document.createElement('canvas');
                            canvas.width = pixel;
                            canvas.height = 1.0 * pixel * height / width;
                            var ctx = canvas.getContext('2d');
                            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
                            canvas.toBlob(function (blob) {
                                const newExamPhoto = new File([blob], "ExamPhoto.jpg", {
                                    type: "image/jpeg",
                                });
                                app.uploadExamPhotos.push(newExamPhoto);
                            });
                        } else {
                            app.uploadExamPhotos.push(examphoto);
                        }
                    } else {
                        if (height > pixel) {
                            var canvas = document.createElement('canvas');
                            canvas.width = 1.0 * pixel * width / height;
                            canvas.height = pixel;
                            var ctx = canvas.getContext('2d');
                            ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
                            canvas.toBlob(function (blob) {
                                const newExamPhoto = new File([blob], "ExamPhoto.jpg", {
                                    type: "image/jpeg",
                                });
                                app.uploadExamPhotos.push(newExamPhoto);
                            });
                        } else {
                            app.uploadExamPhotos.push(examphoto);
                        }
                    }
                };
                img.src = examphoto.content;
            });
        }
    }
});