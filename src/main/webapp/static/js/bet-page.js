function betOnFirst() {
    document.getElementById("on").value = "FIRST";
}

function betOnSecond() {
    document.getElementById("on").value = "SECOND";
}

function calculatePotentialGains() {
    let moneyAttribute = document.getElementById("money");
    let moneyValue = parseFloat(moneyAttribute.value);
    if (moneyValue > 0 && moneyValue < moneyAttribute.getAttribute("max")) {
        let firstCoefficientText = document.getElementById("first-coefficient").innerHTML;
        let secondCoefficientText = document.getElementById("second-coefficient").innerHTML;
        let firstCoefficient = parseFloat(firstCoefficientText.replace(/,/,'.'));
        let secondCoefficient = parseFloat(secondCoefficientText.replace(/,/,'.'));
        let firstPotentialGain = (firstCoefficient - 1) * moneyValue;
        let secondPotentialGain = (secondCoefficient - 1) * moneyValue;
        document.getElementById("first-potential-gain").innerHTML = firstPotentialGain.toFixed(2);
        document.getElementById("second-potential-gain").innerHTML = secondPotentialGain.toFixed(2);
    }
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
