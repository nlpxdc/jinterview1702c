var app = new Vue({
    el: '#app',
    data: {
        address: ''
    },
    mounted() {
        var url = new URL(location.href);
        this.address = url.searchParams.get("address");
        if (!this.address) {
            alert('面试地址不存在');
            return;
        }
        this.getPositionByAddress();
    },
    methods: {
        getPositionByAddress() {
            axios.get('/interview/getPositionByAddress', {
                params: {
                    address: this.address
                }
            })
                .then(function (response) {
                    console.log(response);
                    const position = response.data;

                    var map = new AMap.Map('container', {
                        zoom: 13,
                        center: [position.longitude, position.latitude]
                    });
                    var marker = new AMap.Marker({
                        position: [position.longitude, position.latitude]
                    })
                    map.add(marker);
                })
                .catch(function (error) {
                    console.error(error);
                    alert(error.response.data.message);
                });
        }
    }
});