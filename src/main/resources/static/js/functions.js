async function submitData(){
    let dataObject = {
       editedBy: document.getElementById("name").value,
       dataType: document.getElementById("dataTypeSelectInput").value,
       dataContent: document.getElementById("dataContent").value,
       lifeCycle: document.getElementById("lifeCycleSelect").value,
       maxLifeCycleLevel: document.getElementById("lifeCycleMaxLevel").value,
       validTill: document.getElementById("validTill").value
    }

    let call = await fetch('/saveNewDataObject', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(dataObject)
        });
        let result = await call.json();
        if (!result.error) {
            alert("Success!");
        } else {
            alert(result.message);
        }
}
