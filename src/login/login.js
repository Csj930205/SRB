import React from 'react';
import './css/login.css'
import {useState} from "react";
import {Button} from "react-bootstrap";
import axios from "axios";


function Login() {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');

    const handleId = (e) => {setId(e.target.value)}
    const handlePw = (e) => {setPw(e.target.value)}

    const loginButton = () => {
        if (id.trim() === '') {
            alert('아이디를 입력해주세요.');
            return;
        }

        if (pw.trim() === '') {
            alert('패스워드를 입력해주세요.');
            return;
        }

        const url = "/api/auth/login";
        const data = {id : id, pw : pw}
        const config = {"Content-Type" : 'application/json'}

        axios.post(url, data, config)
            .then(function (res) {
                if (res.data.code === 200) {
                    alert(res.data.message);
                    localStorage.setItem('isLogin', 'true');
                    window.location.href = '/';
                } else {
                    if (res.data.failCount < 5) {
                        alert(res.data.message);
                        return;
                    } else {
                        alert(res.data.message);
                        window.location.href = "/";
                    }
                }
            })
            .catch(error => console.log(error));
    }
    const signupPageMove = () => {
        window.location.href = "/signup";
    }

    return (
        <div className="loginDiv">
            <h3>로그인</h3>
            <br/>
            <div>
                <label>아이디</label>
                <br/>
                <input type="text" value={id} onChange={handleId}/>
            </div>
            <div>
                <label>패스워드</label>
                <br/>
                <input type="password" value={pw} onChange={handlePw}/>
            </div>
            <br/>
            <Button variant="outline-primary" type="button" onClick={loginButton}>로그인</Button>{' '}
            <Button variant="outline-info" type="button" onClick={signupPageMove}>회원가입</Button>
        </div>
    );
}

export default Login;