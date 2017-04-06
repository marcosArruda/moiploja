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
    }else{
        $("#ccHash").val('');
        return false; //dont submit the form;
    }
    return true;
}

$(document).ready(function() {
  $("#checkoutForm").submit(function(event) {
    event.preventDefault();
    return prepareCCHash();
  });
});
