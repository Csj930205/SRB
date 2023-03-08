import React, { useState } from 'react';
import './css/signup.css';
import {Button} from "react-bootstrap";
import axios from "axios";

function Signup() {
    const pwRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/;
    const emailRegEx = /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/i;

    const [id, setId] = useState('');
    const [pw, setPw] = useState('');
    const [pwd, setPwd] = useState('');
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [phone, setPhone] = useState('');

    const handleId = (e) => {setId(e.target.value)};
    const handlePw = (e) => {setPw(e.target.value)};
    const handlePwd = (e) => {setPwd(e.target.value)};
    const handleName = (e) => {setName(e.target.value)};
    const handleEmail = (e) => {setEmail(e.target.value)};
    const handlePhone = (e) => {
        let value = e.target.value
        value = value.replace(/[^0-9]/g, '');
        if (value.length === 11) {
            value = value.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        }
        setPhone(value);
    };
    const signup = () => {
        if(id.trim() === '') {
            alert('아이디를 입력해주세요.');
            return;
        }

        if (pw.trim() === '') {
            alert('패스워드를 입력해 주세요.');
            return;
        }

        if (pwd.trim() === '') {
            alert('패스워드 재입력을 해주세요.');
            return;
        }

        if (name.trim() === '') {
            alert('이름을 입력해주세요.')
            return;
        }

        if (email.trim() === '') {
            alert('이메일을 입력해주세요.')
            return;
        }

        if (phone.trim() === '') {
            alert('핸드폰번호를 입력해 주세요.')
            return;
        }
        if (pw.match(pwRegEx) === null || pwd.match(pwRegEx)=== null) {
            alert('비밀번호 형식을 확인해주세요.')
            return;
        }

        if (pw !== pwd) {
            alert('패스워드가 일치하지 않습니다. 다시 입력해주세요.')
            setPw('');
            setPwd('');
            return;
        }

        if(email.match(emailRegEx) === null) {
            alert('이메일 형식이 일치하지 않습니다.');
            return;
        }
        const url = "/api/auth/signup"
        const data = {id : id, pw : pw, name : name, phone : phone, email : email, role : "ROLE_USER"}
        const config = {"Content-Type" : 'application/json'}
        axios.post(url, data, config)
            .then(function (res) {
                if(res.data.code === 200) {
                    alert(res.data.message)
                    window.location.href = "/";
                } else {
                    alert(res.data.message)
                }
            })
            .catch(error => console.log(error));
    }

    return (
            <div className="signupDiv">
                <h3>회원가입</h3>
                <br/>
                <div>
                    <label className="labelzz">아이디</label>
                    <br/>
                    <input type="text" value={id} onChange={handleId}/>
                </div>
                <div>
                    <label>패스워드</label>
                    <br/>
                    <input type="password" value={pw} onChange={handlePw}/>
                </div>
                <div>
                    <label>패스워드 재입력</label>
                    <br/>
                    <input type="password" value={pwd} onChange={handlePwd}/>
                </div>
                <div>
                    <label>이름</label>
                    <br/>
                    <input type="text" value={name} onChange={handleName}/>
                </div>
                <div>
                    <label>이메일</label>
                    <br/>
                    <input type="text" value={email} onChange={handleEmail}/>
                </div>
                <div>
                    <label>휴대폰번호</label>
                    <br/>
                    <input type="text"value={phone} onChange={handlePhone}/>
                </div>
                <br/>
                <Button variant='primary' type="button" onClick={signup}>가입</Button>
            </div>
    );
}

export default Signup;