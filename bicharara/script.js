document.getElementById("register-form").addEventListener("submit", function(e) {
    e.preventDefault();

    const nombre = document.getElementById("register-name").value;
    const email = document.getElementById("register-email").value;
    const password = document.getElementById("register-password").value;

    fetch("registro.php", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ nombre, email, password })
    })
    .then(res => res.json())
    .then(data => {
        if (data.success) {
            alert("Registro exitoso. Ahora puedes iniciar sesión.");
            // Aquí podrías cambiar de panel al de login
        } else {
            alert("Error: " + data.message);
        }
    })
    .catch(err => {
        console.error(err);
        alert("Hubo un problema al registrar.");
    });
});
