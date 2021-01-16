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
        let firstBetsAmount = parseFloat(document.getElementById("first-bets-amount").innerHTML);
        let secondBetsAmount = parseFloat(document.getElementById("second-bets-amount").innerHTML);
        let firstCoefficient = calculateCoefficient("FIRST", firstBetsAmount + moneyValue, secondBetsAmount);
        let secondCoefficient = calculateCoefficient("SECOND", firstBetsAmount, secondBetsAmount + moneyValue);
        document.getElementById("first-coefficient").innerHTML = (firstCoefficient + 1).toFixed(3);
        document.getElementById("second-coefficient").innerHTML = (secondCoefficient + 1).toFixed(3);
        let firstPotentialGain;
        let secondPotentialGain;
        if (firstBetsAmount + secondBetsAmount === 0.0) {
            firstCoefficient = 1;
            secondCoefficient = 1;
        }
        firstPotentialGain = firstCoefficient * moneyValue;
        secondPotentialGain = secondCoefficient * moneyValue;
        document.getElementById("first-potential-gain").innerHTML = firstPotentialGain.toFixed(2);
        document.getElementById("second-potential-gain").innerHTML = secondPotentialGain.toFixed(2);
    } else {
        document.getElementById("first-potential-gain").innerHTML = '-';
        document.getElementById("second-potential-gain").innerHTML = '-';
    }
}

function calculateCoefficient(team, firstBetsAmount, secondBetsAmount) {
    if (firstBetsAmount * secondBetsAmount === 0.0) {
        return 0.0;
    }
    let result;
    if (team === "SECOND") {
        result = (firstBetsAmount / secondBetsAmount);
    } else {
        result = (secondBetsAmount / firstBetsAmount);
    }
    let commission = parseFloat(document.getElementById("commission").innerHTML);

    result -= result * commission / 100;
    return result;
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
