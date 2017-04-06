$(document).ready(function() {
  $("#pay_cc").click(function() {
    var cc = new Moip.CreditCard({
      number  : $("#cc_number").val(),
      cvc     : $("#cc_cvc").val(),
      expMonth: $("#cc_exp_month").val(),
      expYear : $("#cc_exp_year").val(),
      pubKey  : $("#public_key").val()
    });
    if( cc.isValid()){
      $("#hash").val(cc.hash());
    } else {
      $("#hash").val('');
      alert('Invalid credit card. Verify parameters: number, cvc, expiration Month, expiration Year');
      return false; // Don't submit the form
    }
  });
});
