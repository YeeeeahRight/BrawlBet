const secondTeamField = document.getElementById("secondTeam");

secondTeamField.addEventListener("input", function (event) {
    let secondTeam = document.getElementById("secondTeam").value;
    let firstTeam = document.getElementById("firstTeam").value;
    if (firstTeam.toUpperCase() === secondTeam.toUpperCase()) {
        let lang = document.getElementById("html").getAttribute("lang");
        let message;
        switch (lang) {
            case "ru":
                message = "Команды не могут иметь одинаковое имя";
                break;
            case "be":
                message = "Каманды ня могуць мець аднолькавае імя.";
                break;
            case "en":
                message = "Teams cannot have the same name.";
                break;
        }
        secondTeamField.setCustomValidity(message);
    } else {
        secondTeamField.setCustomValidity("");
    }
});
