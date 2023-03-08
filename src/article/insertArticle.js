import React, {useState} from 'react';
import {Button, Form} from "react-bootstrap";
import {Row} from "react-bootstrap";
import {Col} from "react-bootstrap";
import axios from "axios";

function InsertArticle() {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const handleTitle = (e) => {setTitle(e.target.value)}
    const handleContent = (e) =>{setContent(e.target.value)}

    const homeButton = () => {
        window.location.href = "/";
    }

    const insertButton = () => {
        if (title.trim() === '') {
            alert('제목을 입력해주세요.')
            return;
        }
        const data = {title : title, content : content};
        const url = "/api/article/insertArticle";
        const config = {"Content-Type" : 'application/json'}
        axios.post(url, data, config)
            .then(function (res) {
                if (res.data.code === 200) {
                    alert(res.data.message);
                    window.location.href = "/article";
                } else {
                    alert(res.data.message);
                    return;
                }
            })
            .catch(error => console.log(error))
    };

    return (
        <Form>
            <Form.Group as={Row} className="mb-3">
                <Form.Label column sm="2">
                    제목
                <Col sm="10">
                    <Form.Control type="text" value={title} onChange={handleTitle}/>
                </Col>
                </Form.Label>
            </Form.Group>
            <Form.Group as={Row} className="mb-3" >
                <Form.Label column sm="2">
                    내용
                <Col sm="10">
                    <Form.Control as="textarea" value={content} onChange={handleContent}/>
                </Col>
                </Form.Label>
            </Form.Group>
            <>
                <Button variant="outline-danger" type="button" onClick={homeButton}>메인페이지</Button>{' '}
                <Button variant="outline-primary" type="button" onClick={insertButton}>등록</Button>
            </>
        </Form>
    );
}

export default InsertArticle;