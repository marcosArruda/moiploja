function prepareCCHash(){

    var cc = new Moip.CreditCard({
          number  : $("#ccNumber").val(),
          cvc     : $("#cvv").val(),
          expMonth: $("#expire_month").val(),
          expYear : $("#expire_year").val(),
          pubKey  : $("#public_key").val()
        });

    if(cc.isValid()){
        $("#ccHash").val(cc.hash());
        //alert("O Hash: " + $("#ccHash").val());
        return true;
    }else{
        $("#ccHash").val('INVALID');
        //alert("Hash invalido");
        return false; //dont submit the form;
    }

}

function copyFields(){
    $("#firstName").val($("#billingFirstName").val());
    $("#lastName").val($("#billingLastName").val());
    $("#addressLine1").val($("#billingAddressLine1").val());
    $("#addressLine2").val($("#billingAddressLine2").val());
    $("#city").val($("#billingCity").val());
    $("#state").val($("#billingState").val());
    $("#zipCode").val($("#billingZipCode").val());
}

$(document).ready(function() {
    $("#place_order").click(function(event) {
        return prepareCCHash();
    });
    $("#ship-to-different-address-checkbox").change(function(){
        if(this.checked){
            copyFields();
        }
    });
});
