var app = new Vue({
    el: '#app',
    data: {
        loading: false,
        finished: false,
        keyword: '',
        originKeyword: '',
        onlyme: false,
        time: '',
        interviews: [],
        active: 0,
    },
    mounted() {
        console.log('view mounted');

    },
    watch: {
        onlyme() {
            this.time = '';
            this.interviews = [];
            this.searchInterview();
        }
    },
    methods: {
        onLoad() {
            console.log('on load');
            const lastInterview = this.interviews.length ? this.interviews[this.interviews.length - 1] : '';
            this.time = lastInterview ? lastInterview.timestamp : '';
            this.searchInterview();
        },
        searchInterview() {
            this.originKeyword = this.keyword;

            axios.get('/interview/search', {
                params: {
                    keyword: this.keyword,
                    onlyme: this.onlyme,
                    time: this.time
                }
            })
                .then(function (response) {
                    console.log(response);
                    var newInterviews = response.data;
                    app.interviews = app.interviews.concat(newInterviews);
                    app.loading = false;
                    app.finished = newInterviews < 10;
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.response.data.message);
                });
        },
        handleSearchClick() {
            console.log('search click');
            if (this.keyword !== this.originKeyword) {
                this.time = '';
                this.interviews = [];
                this.searchInterview();
            }
        },
        handleDownloadClick() {
            axios(
                {
                    baseURL: "/jinterviewback",
                    url: "/interview/downloadinterview",
                    method: "get",
                    responseType: 'blob',
                    header: {
                        'jinterviewToken': localStorage['jinterviewToken']
                    }
                }
            )
                .then(res => {

                    console.log(res)
                    const fileName = '面试记录.xls';
                    if ('download' in document.createElement('a')) { // 非IE下载
                        const blob = new Blob([res.data], { type: 'application/ms-excel' });
                        const elink = document.createElement('a');
                        elink.download = fileName;
                        elink.style.display = 'none';
                        elink.href = URL.createObjectURL(blob);
                        document.body.appendChild(elink);
                        elink.click();
                        URL.revokeObjectURL(elink.href); // 释放URL 对象
                        document.body.removeChild(elink);
                    }

                })
                .catch(err => {
                    console.error(err);
                })
        },
        onRefresh() {
            console.log('pull refresh trigger');
            this.finished = false;
            this.time = '';
            this.interviews = [];
            this.searchInterview();
        },
        handleInterviewTouch(interview) {
            console.log('interview touch', interview);
            location.href = 'interview-show.html?interviewId=' + interview.interviewId;
        },
        handleCreateTouch() {
            console.log('create interview touch');
            location.href = 'interview-create.html';
        },
        handleMeTouch() {
            console.log('me touch');
            location.href = 'student-show.html';
        }
    }
});