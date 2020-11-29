$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
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
        }
    );
});

function enable(checkbox, id) {
    const enabled = checkbox.is(":checked");

    $.ajax({
        url: "ajax/admin/users/" + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        checkbox.closest("tr").attr("data-userEnabled", enabled);
        successNoty("Updated")
    });
}