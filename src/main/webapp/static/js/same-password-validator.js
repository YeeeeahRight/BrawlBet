const repeatedPasswordField = document.getElementById("repeated-password");

repeatedPasswordField.addEventListener("input", function (event) {
    let passwordValue = document.getElementById("password").value;
    let repeatedPasswordValue = document.getElementById("repeated-password").value;
    console.log(passwordValue);
    console.log(repeatedPasswordValue);
    if (passwordValue.toUpperCase() !== repeatedPasswordValue.toUpperCase()) {
        let lang = document.getElementById("html").getAttribute("lang");
        let message;
        switch (lang) {
            case "ru":
                message = "Пароли должны совпадать.";
                break;
            case "be":
                message = "Паролi павiнны супадаць.";
                break;
            case "en":
                message = "Passwords must match.";
                break;
        }
        repeatedPasswordField.setCustomValidity(message);
    } else {
        repeatedPasswordField.setCustomValidity("");
    }
});