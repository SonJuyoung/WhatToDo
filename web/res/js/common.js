let rtBtns = document.querySelectorAll(".rtBtn");
let ppBtns = document.querySelectorAll(".ppBtn");
let kdBtns = document.querySelectorAll(".kdBtn");
let thmBtns = document.querySelectorAll(".thmBtn");
let pnBtns = document.querySelectorAll(".pnBtn");
let rtLbElem = document.querySelector("#rtLb");
let ppLbElem = document.querySelector("#ppLb");
let kdLbElem = document.querySelector("#kdLb");
let thmLbElem = document.querySelector("#thmLb");
let pnLbElem = document.querySelector("#pnLb");

function rtClickedRmv() {
    for (let i=0; i<rtBtns.length; i++) {
        rtBtns[i].classList.remove("clicked");
    }
}

function ppClickedRmv() {
    for (let i=0; i<ppBtns.length; i++) {
        ppBtns[i].classList.remove("clicked");
    }
}

function kdClickedRmv() {
    for (let i=0; i<kdBtns.length; i++) {
        kdBtns[i].classList.remove("clicked");
    }
}
function thmClickedRmv() {
    for (let i=0; i<thmBtns.length; i++) {
        thmBtns[i].classList.remove("clicked");
    }
}

function pnClickedRmv() {
    for (let i=0; i<pnBtns.length; i++) {
        pnBtns[i].classList.remove("clicked");
    }
}

for (let i = 0; i < rtBtns.length; i++) {
    rtBtns[i].addEventListener("click", function (e) {
        e.preventDefault();
        if(e.target.classList.contains("clicked")) {
            rtClickedRmv();
        } else {
            rtClickedRmv();
            rtBtns[i].classList.add("clicked");
        }
    })
}
// const rtClicked = rtLbElem.querySelector(".clicked")
// if(rtClicked) {
//     rtClicked.addEventListener('click', rtClicked.classList.remove("clicked"));
// }

// rtBtns.forEach(function (item) {
//     item.addEventListener('click', function (e) {
//         e.preventDefault();
//         rtClickedRmv();
//         item.classList.add("clicked");
//     })
// });
ppBtns.forEach(function (item) {
    item.addEventListener('click', function (e) {
        e.preventDefault();
        if(e.target.classList.contains("clicked")) {
            ppClickedRmv();
        } else {
            ppClickedRmv();
            item.classList.add("clicked");
        }
    })
});
kdBtns.forEach(function (item) {
    item.addEventListener('click', function (e) {
        e.preventDefault();
        if(e.target.classList.contains("clicked")) {
            kdClickedRmv();
        } else {
            kdClickedRmv();
            item.classList.add("clicked");
        }
    })
});
thmBtns.forEach(function (item) {
    item.addEventListener('click', function (e) {
        e.preventDefault();
        if(e.target.classList.contains("clicked")) {
            thmClickedRmv();
        } else {
            thmClickedRmv();
            item.classList.add("clicked");
        }

    })
});
pnBtns.forEach(function (item) {
    item.addEventListener('click', function (e) {
        e.preventDefault();
        if(e.target.classList.contains("clicked")) {
            pnClickedRmv();
        } else {
            pnClickedRmv();
            item.classList.add("clicked");
        }

    })
});