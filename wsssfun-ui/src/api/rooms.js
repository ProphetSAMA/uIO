import axios from 'axios';

axios.get('/api/rooms')
    .then(response => {
        console.log(response.data); // 处理返回的数据
    })
    .catch(error => {
        console.error('There was an error!', error); // 错误处理
    });
