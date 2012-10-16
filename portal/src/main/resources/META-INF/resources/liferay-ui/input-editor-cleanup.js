var A = AUI();
A.all('.cke_dialog_background_cover').remove();
A.all('div[role=dialog]').remove();
A.one('body').removeAttribute('onpageshow');