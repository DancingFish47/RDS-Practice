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

async function findData(){
  let dataTypeSelect = document.getElementById("dataTypeSelectOutput");
  let dataTypeName = dataTypeSelect.options[dataTypeSelect.selectedIndex].text;
  let date = document.getElementById("dateOutput").value;
  if(dataTypeSelect.options[dataTypeSelect.selectedIndex].value == "null") dataTypeName = null;

  var url = new URL(window.location);
// If your expected result is "http://foo.bar/?x=1&y=2&x=42"
  if(date !== null) url.searchParams.set('date', date);
  else url.searchParams.delete('date');
  if(dataTypeName !== null) url.searchParams.set('dataType', dataTypeName);
  else url.searchParams.delete('dataType');
  url.searchParams.set('page', 1);

  window.location.replace(url);
}
