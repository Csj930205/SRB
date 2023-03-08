import React from 'react';
import Nav from "react-bootstrap/Nav";
import { Link } from "react-router-dom";
import axios from "axios";


function Header() {
    const isLogin = localStorage.getItem('isLogin');
    const url = '/api/member/logout';

    const logout = () => {
        const url = "/api/auth/logout";
        const config = {"Content-Type" : 'application/json'};

        axios.post(url, config)
            .then(function (res) {
                if (res.data.code === 200) {
                    alert(res.data.message);
                    localStorage.clear();
                    window.location.href = "/";
                }
            })
            .catch(error => console.log(error))
    };

    return (
        <div>
            <Nav variant='tabs'>
                <Nav.Item>
                    <Nav.Link as={Link} to='/'>Home</Nav.Link>
                </Nav.Item>
                { isLogin === 'true' ? (
                    <>
                        <Nav.Item>
                            <Nav.Link as={Link} to='/article'>게시판</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link as={Link} to='/songList'>노래게시판</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link onClick={logout}>로그아웃</Nav.Link>
                        </Nav.Item>
                    </>
                ) : (
                <>
                        <Nav.Item>
                            <Nav.Link as={Link} to='/login'>로그인</Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link as={Link} to='/signup'>회원가입</Nav.Link>
                        </Nav.Item>
                </>
                    )
                }
            </Nav>
        </div>
    );
}

export default Header;