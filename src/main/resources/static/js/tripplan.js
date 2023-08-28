const options = {
    valueNames: ['id', 'location', 'budget', 'period', 'members']
};

// Init list
const contactList = new List('contacts', options);

let idField = $('#id-field'),
    locationField = $('#location-field'),
    budgetField = $('#budget-field'),
    periodField = $('#period-field'),
    membersField = $('#members-field'),
    addBtn = $('#add-btn'),
    editBtn = $('#edit-btn').hide(),
    removeBtns = $('.remove-item-btn'),
    editBtns = $('.edit-item-btn');

// Sets callbacks to the buttons in the list
refreshCallbacks();

addBtn.click(function() {
    contactList.add({
        id: Math.floor(Math.random()*110000),
        location: locationField.val(),
        budget: budgetField.val(),
        period: periodField.val(),
        members: membersField.val()
    });
    clearFields();
    refreshCallbacks();
});

editBtn.click(function() {
    const item = contactList.get('id', idField.val())[0];
    item.values({
        id:idField.val(),
        location: locationField.val(),
        budget: budgetField.val(),
        period: periodField.val(),
        members: membersField.val()
    });
    clearFields();
    editBtn.hide();
    addBtn.show();
});

function refreshCallbacks() {
    // Needed to add new buttons to jQuery-extended object
    removeBtns = $(removeBtns.selector);
    editBtns = $(editBtns.selector);

    removeBtns.click(function() {
        const itemId = $(this).closest('tr').find('.id').text();
        contactList.remove('id', itemId);
    });

    editBtns.click(function() {
        const itemId = $(this).closest('tr').find('.id').text();
        const itemValues = contactList.get('id', itemId)[0].values();
        idField.val(itemValues.id);
        locationField.val(itemValues.location);
        budgetField.val(itemValues.budget);
        periodField.val(itemValues.period);
        membersField.val(itemValues.members);

        editBtn.show();
        addBtn.hide();
    });
}

function clearFields() {
    locationField.val('');
    budgetField.val('');
    periodField.val('');
    membersField.val('');
}