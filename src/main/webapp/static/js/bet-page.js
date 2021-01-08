function betOnFirst() {
    document.getElementById("on").value = "FIRST";
}

function betOnSecond() {
    document.getElementById("on").value = "SECOND";
}


const money = document.getElementById("money");

money.addEventListener("input", function (event) {
    let lang = document.getElementById("html").getAttribute("lang");
    if (money.getAttribute("max") === "0") {
        let message;
        switch (lang) {
            case "ru":
                message = "У вас нет денег для ставки.";
                break;
            case "be":
                message = "У вас няма грошай для стаўкі.";
                break;
            case "en":
                message = "You have no money for bet.";
                break;
        }
        money.setCustomValidity(message);
    } else {
        money.setCustomValidity("");
    }
});
