/*
 * Additional methods for the jQuery validation plugin.
 * 
 */
jQuery.validator.addMethod("laterThan", function(value, element, params) {

    if (!/Invalid|NaN/.test(new Date(value))) {
        return new Date(value) > new Date($(params).val());
    }

}, 'Must be later than starting time.');

jQuery.validator.addMethod("soonerThan", function(value, element, params) {

    if (!/Invalid|NaN/.test(new Date(value))) {
        return new Date(value) < new Date($(params).val());
    }

}, 'Must be sooner than ending time.');