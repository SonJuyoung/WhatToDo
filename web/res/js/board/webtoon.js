crawlingBtn = document.querySelector('.crawlingBtn');
resultBox = document.querySelector('.result_box');
resultNm = document.querySelector('.result_nm');
randomSubmitBtn = document.querySelector('#randomSubmitBtn');
choiceBtnSection = document.querySelector('.choiceBtn_section');
choiceBtn = choiceBtnSection.querySelectorAll('button');

crawlingBtn.addEventListener('click', function () {
    location.href = '/board/webtooncrawling';
})

var webtoonGenreRandomUrl = '/board/webtoonGenreRandom'


choiceBtn.forEach(function (item) {
    item.addEventListener('click', function (e) {
        let btnName = null;
        console.log('실행');
        if (e.target.innerText == '일상') {
            btnName = '일상 ';
        } else if (e.target.innerText == '개그') {
            btnName = '개그 ';
        } else if (e.target.innerText == '판타지') {
            btnName = '판타지';
        } else if (e.target.innerText == '액션') {
            btnName = '액션 ';
        } else if (e.target.innerText == '드라마') {
            btnName = '드라마';
        } else if (e.target.innerText == '순정') {
            btnName = '순정 ';
        } else if (e.target.innerText == '감성') {
            btnName = '감성 ';
        } else if (e.target.innerText == '스릴러') {
            btnName = '스릴러';
        } else if (e.target.innerText == '시대극') {
            btnName = '시대극';
        } else if (e.target.innerText == '스포츠') {
            btnName = '스포츠';
        } else if (e.target.innerText == '완결') {
            btnName = '완결';
        }

        console.log('선택 버튼 : ' + btnName);
        webtoonGenreBtnClickRandom(webtoonGenreRandomUrl, btnName);
    })
});

// RANDOM 버튼 눌렀을 때
function webtoonGenreRandom(url) {
    fetch(url).then((res) => {
        return res.json();
    }).then((data) => {// 사이즈 구하는 법 : Object.keys(data).length
        let randomNum = Math.floor((Math.random() * data.length));  // 랜덤 숫자
        console.log(data[randomNum].img);
        resultDisplay(data[randomNum].img, data[randomNum].link, data[randomNum].nm, data[randomNum].writer, 'RANDOM');
    }).catch((err) => {
        console.log(err);
    });
}

// 장르 버튼 눌렀을 때
function webtoonGenreBtnClickRandom(url, btnGenre) {
    fetch(url).then((res) => {
        return res.json();
    }).then((data) => {
        let record = filterFunction(data, btnGenre);
        let randomNum = Math.floor((Math.random() * record.length));  // 랜덤 숫자
        resultDisplay(record[randomNum].img, record[randomNum].link, record[randomNum].nm, record[randomNum].writer, btnGenre);
    }).catch((err) => {
        console.log(err);
    });
}

function filterFunction(data, genre) {
    return data.filter(function (item, index, arr) {
        if(genre=='완결') {
            return item.state = genre;
        } else return item.genre == genre;
    });
}


function resultDisplay(webtoonimg, webtoonLink, webtoonNm, webtoonWriter, btnGenre) {
    resultBox.innerHTML = `<div id="genre_title"><b>${btnGenre}</b></div>
<div><a href="${webtoonLink}"><img src="${webtoonimg}"></a></div>
    <div><a href="${webtoonLink}"><span class="font-14px"><b>${webtoonNm}</b></span></a></div>
    <div><span class="font-14px">${webtoonWriter}</span></div>
`;
}

randomSubmitBtn.addEventListener('click', function () {  // 랜덤버튼 누르면 무작위 랜덤웹툰 출력
    webtoonGenreRandom(webtoonGenreRandomUrl);
})

/* --- MODAL 관련 --- */

const modalWindow = document.getElementById("modal");
const modalXBtn = document.querySelector(".close-area");
const webtoonModalElem = document.querySelectorAll('.webtoonModalElement');
const modalContent = document.querySelector('.modalContent');
const reviewListElem = document.querySelector('#review_list');
let dataNm, dataWeekend, iuser, writerName;

webtoonModalElem.forEach(function (item) {
    item.addEventListener('click', function (e) {

        console.log(item.dataset.nm);
        console.log(item.dataset.weekend);
        console.log(item.dataset.iuser);
        console.log(item.dataset.writernm);

        // 데이터 담기
        dataNm = item.dataset.nm;
        dataWeekend = item.dataset.weekend;
        iuser = item.dataset.iuser;
        writerName = item.dataset.writernm;

        modalWindow.style.display = 'flex';

        modalContent.innerHTML = item.innerHTML;

        getCmtList();
        delCmtList();
    })
})

modalXBtn.addEventListener('click', () => {
    modalWindow.style.display = 'none';
    reviewFrm.ctnt.value = null;
})
window.addEventListener("keyup", (e) => {
    if (modalWindow.style.display === "flex" && e.key === "Escape") {
        modalWindow.style.display = 'none';
        reviewFrm.ctnt.value = null;
    }

});
window.addEventListener("mouseup", (e) => { // 모달창 밖으로 마우스 클릭하면 닫힘
    if (modalWindow.style.display === "flex" && e.target === modalWindow) {
        modalWindow.style.display = 'none';
        reviewFrm.ctnt.value = null;
    }
});

/* -------- MODAL REVIEW 관련 -------- */
const reviewFrm = document.querySelector('#reviewFrm');

reviewFrm.addEventListener('submit', (e)=> {    //input-text ctnt에서 엔터치면 submit날아가기 때문에 막는다.
    e.preventDefault();
});

// 리뷰 작성 버튼 분기문
reviewFrm.btn_submit.addEventListener('click', () => {
    const reviewVal = reviewFrm.ctnt.value;
    if(reviewVal.length === 0) {
        alert('리뷰 내용을 작성해 주세요.');
    } else if(isWrongWith('ctnt', reviewVal)) {
        alert(msg.ctnt);
    } else if(iuser=='') {
        alert('로그인 해주세요.');
    } else { //리뷰 insert 시도
        insReviewWebtoonAjax(reviewVal);
    }
});

// 리뷰 Insert
const insReviewWebtoonAjax = (val) => {
    const param = {
        'nm': dataNm,
        'weekend': dataWeekend,
        'iuser': iuser,
        'ctnt': val
    };
    myFetch.post('/board/review', (data) => {
        console.log('result : ' + data.result);
        switch(data.result) {
            case 0:
                alert('리뷰 등록에 실패하였습니다.');
                break;
            default:
                //기존 table태그가 있는지 확인

                let table = reviewListElem.querySelector('table');
                if(!table) {
                    reviewListElem.innerHTML = null;
                    table = makeTable();
                    reviewListElem.appendChild(table);
                }
                const item = {
                    rnum:data.result,
                    nm: dataNm,
                    iuser: iuser,
                    nickname: data.resultNickname,
                    ctnt: val,
                }
                const tr = makeTr(item);
                table.appendChild(tr);

                reviewFrm.ctnt.value = null;
                break;
        }
    }, param);
}

//통신 시작!!!
const getCmtList = () => {
    let nm = dataNm;
    console.log('데이터 통신 Nm : ' + nm);
    myFetch.get(`/board/review/${nm}`, setCmtList);
}

const delCmtList = () => {
    const reviewListElem = document.querySelector('#review_list');
    reviewListElem.innerHTML = '';
}

//통신 결과물 세팅
const setCmtList = (list) => {
    const reviewListElem = document.querySelector('#review_list');

    // 평가 없을 때
    if(list.length === 0) {
        reviewListElem.innerHTML = '<span style="font-size: 14px">첫 리뷰의 주인공이 되어주세요.</span>';
        return;
    }

    const table = makeTable();
    reviewListElem.appendChild(table);

    list.forEach(item => {
        const tr = makeTr(item);
        table.appendChild(tr);
    });
}

const makeTable = () => {
    const table = document.createElement('table');
    table.innerHTML = `
            <tr>
                <th>리뷰내용</th>
                <th>닉네임</th>
                <th></th>
            </tr>`;
    return table;
}

const makeTr = item => {
    const tr = document.createElement('tr');

    tr.innerHTML = `
                <td>${item.ctnt}</td>
                <td>
                    <span>${item.nickname}</span>
                </td>
            `;
    const td = document.createElement('td');
    tr.appendChild(td);

    console.log('게시글에 적힌 iuser :' + item.iuser);
    console.log('내 iuser : ' + iuser);
    if(parseInt(iuser) === parseInt(item.iuser)) {
        const modBtn = document.createElement('input');
        modBtn.type = 'button';
        modBtn.value = '수정';
        modBtn.classList.add('modBtn');

        const delBtn = document.createElement('input');
        delBtn.type = 'button';
        delBtn.value = '삭제';
        delBtn.classList.add('delBtn');

        modBtn.addEventListener('click', () => {
            console.log('수정 버튼 클릭');
            const tdArr = tr.querySelectorAll('td');
            const tdCell = tdArr[0];

            const modInput = document.createElement('input');
            modInput.value = item.ctnt;
            const saveBtn = document.createElement('input')
            saveBtn.type = 'button';
            saveBtn.value = '저장';
            saveBtn.addEventListener('click', () => {
                const param = {
                    rnum: item.rnum,
                    ctnt: modInput.value
                }
                myFetch.put('/board/review', data => {
                    switch(data.result) {
                        case 0:
                            alert('댓글 수정에 실패하였습니다.')
                            break;
                        case 1:
                            tdCell.innerText = modInput.value;
                            item.ctnt = modInput.value;
                            removeCancelBtn();
                            break;
                    }
                }, param);
            });

            tdCell.innerHTML = null;
            tdCell.appendChild(modInput);
            tdCell.appendChild(saveBtn);

            const cancelBtn = document.createElement('input');
            cancelBtn.type = 'button';
            cancelBtn.value = '취소';
            cancelBtn.addEventListener('click', () => {
                tdCell.innerText = item.ctnt;
                removeCancelBtn();
            });

            const removeCancelBtn = () => {
                modBtn.classList.remove('hidden');
                delBtn.classList.remove('hidden');
                cancelBtn.remove();
            }

            td.insertBefore(cancelBtn, modBtn);
            modBtn.classList.add('hidden');
            delBtn.classList.add('hidden');
        });

        // 댓글 삭제 이벤트
        delBtn.addEventListener('click', () => {
            if(confirm('삭제하시겠습니까?')) {
                console.log(item.rnum);
                delCmt(item.rnum, tr);
            }
        });

        td.appendChild(modBtn);
        td.appendChild(delBtn);
    }
    return tr;
}

const delCmt = (rnum, tr) => {
    myFetch.delete(`/board/review/${rnum}`, data => {
        if(data.result) {
            tr.remove();
        } else {
            alert('댓글을 삭제할 수 없습니다.');
        }
    });
}

