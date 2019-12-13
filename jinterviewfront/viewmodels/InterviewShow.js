var app = new Vue({
    el: '#app',
    data: {
        interview: {},
        stars: 0,
        images: [],
        interviewId: 0,
        Status: ['待面试', 'OFFER', '等通知', '凉凉', '复试'],
    },
    methods: {
        mounted() {
            console.log('view mounted');

            var url = new URL(location.href);
            this.interviewId = url.searchParams.get("interviewId");
            if (!this.interviewId) {
                alert('interviewId 不存在');
                return;
            }
            this.getInterviewById();
        },
        getInterviewById(interviewId) {
            axios.get("/interview/getById", {
                params: {
                    interviewId: this.interviewId
                }
            })
                .then(res => {
                    console.log(res)
                    this.interview = res.data;
                    this.stars = res.data.stars;
                    this.images = res.data.examphotoUrls;

                })
                .catch(err => {
                    console.error(err);
                })
        },

    }
});