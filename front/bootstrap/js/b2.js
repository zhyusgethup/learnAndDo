$(document).ready(function() {
    $('#example-getting-started').multiselect(
        {
            nonSelectedText: '请选择'
        }
    );
    var text1 = '<option value="1" selected = "true">广东省</option>';
    var text2 = '<option value="2">四川省</option>';
    var text3 = '<option value="3" selected = "true">山西省</option>';
    $("#active-select").append(text1);
    $("#active-select").append(text2);
    $("#active-select").append(text3);
    $('#active-select').multiselect();
});