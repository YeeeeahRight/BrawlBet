function betOnFirst() {
    document.getElementById("on").value = "FIRST";
}

function betOnSecond() {
    document.getElementById("on").value = "SECOND";
}


const firstPotentialGainField = document.getElementById("first-potential-gain");
const secondPotentialGainField = document.getElementById("second-potential-gain");
const firstCoefficientField = document.getElementById("first-coefficient");
const secondCoefficientField = document.getElementById("second-coefficient");
const initialFirstCoefficient = firstCoefficientField.innerHTML;
const initialSecondCoefficient = secondCoefficientField.innerHTML;

function calculatePotentialGains() {
    let moneyAttribute = document.getElementById("money");
    let moneyValue = parseFloat(moneyAttribute.value);
    if (moneyValue > 0 && moneyValue <= moneyAttribute.getAttribute("max")) {
        let firstBetsAmount = parseFloat(document.getElementById("first-bets-amount").innerHTML);
        let secondBetsAmount = parseFloat(document.getElementById("second-bets-amount").innerHTML);
        let firstCoefficient = calculateCoefficient("FIRST", firstBetsAmount + moneyValue, secondBetsAmount);
        let secondCoefficient = calculateCoefficient("SECOND", firstBetsAmount, secondBetsAmount + moneyValue);
        firstCoefficientField.innerHTML = (firstCoefficient + 1).toFixed(3);
        secondCoefficientField.innerHTML = (secondCoefficient + 1).toFixed(3);
        if (firstBetsAmount + secondBetsAmount === 0.0) {
            firstCoefficient = 1;
            secondCoefficient = 1;
        }
        let firstPotentialGain = firstCoefficient * moneyValue;
        let secondPotentialGain = secondCoefficient * moneyValue;
        firstPotentialGainField.innerHTML = firstPotentialGain.toFixed(2);
        secondPotentialGainField.innerHTML = secondPotentialGain.toFixed(2);
    } else {
        firstPotentialGainField.innerHTML = '-';
        secondPotentialGainField.innerHTML = '-';
        firstCoefficientField.innerHTML = initialFirstCoefficient
        secondCoefficientField.innerHTML = initialSecondCoefficient;
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
    let commissionStr = document.getElementById("commission").innerHTML;
    let commission = parseFloat(commissionStr);

    result -= result * commission / 100;
    return result;
}

const money = document.getElementById("money");

money.addEventListener("input", function (event) {
    if (money.getAttribute("max") === "0.0") {
        let lang = document.getElementById("html").getAttribute("lang");
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
