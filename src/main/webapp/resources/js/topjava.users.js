var ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "admin/users/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        }),
        updateTable: function () {
            $.get("admin/users/", updateTableByData);
        }
    };
    makeEditable();
});

function enable(checkbox, id) {
    const enabled = checkbox.is(":checked");

    $.ajax({
        url: "admin/users/" + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        checkbox.closest("tr").attr("data-userEnabled", enabled);
        successNoty("Updated")
    });
}