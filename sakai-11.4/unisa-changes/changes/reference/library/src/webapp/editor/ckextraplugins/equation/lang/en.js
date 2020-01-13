
/*CKEDITOR.plugins.setLang( 'equation', 'en',
{
	equation :
	{
		title		: 'CodeCogs Equation Editor',
		menu    : 'Equation',
		toolbar		: 'Create Equation',
		edit		: 'Edit Equation',
	}
});
*/

CKEDITOR.addPluginLang = function( plugin, lang, obj )
{
    // v3 using feature detection
    if (CKEDITOR.skins)
    {
        var newObj = {};
        newObj[ plugin ] = obj;
        obj = newObj;
    }
    CKEDITOR.plugins.setLang( plugin, lang, obj );
}

		CKEDITOR.plugins.setLang( 'equation', 'en',
				{
					equation :
					{
						title		: 'CodeCogs Equation Editor',
						menu    : 'Equation',
						toolbar		: 'Create Equation',
						edit		: 'Edit Equation',
					}
				});