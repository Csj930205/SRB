import React, {useEffect, useState} from 'react';
import axios from "axios";
import {Table} from "react-bootstrap";
import {Button} from "react-bootstrap";

function Article() {
    const [articleList, setArticleList] = useState([]);
    const url = "/api/article/list"

    useEffect(() =>{
        async function getArticle() {
            const response = await axios.get(url);
                setArticleList(response.data.articleList);
        }
        getArticle();
    }, []);

    const insertPage = () => {window.location.href = "/insertArticle"}
    return (
        <div>
            <h2>게시판</h2>
            <Table striped bordered hover variant="dark">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>내용</th>
                </tr>
                </thead>
                <tbody>
                {articleList.map(item => (
                    <tr key={item.seq}>
                        <td>{item.seq}</td>
                        <td>{item.title}</td>
                        <td>{item.content}</td>
                    </tr>
                ))}

                </tbody>
            </Table>
            <>
                <Button variant="outline-primary" type="button" onClick={insertPage}>등록</Button>{' '}
                <Button variant="outline-danger" type="button">삭제</Button>
            </>

        </div>
    );
}

export default Article;