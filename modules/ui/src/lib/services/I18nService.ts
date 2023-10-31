import i18n from 'sveltekit-i18n';

const languages = ['en', 'fr'];
const i18nFiles = ['common', 'app', 'project', 'module', 'environment'];

const langFilePairs: any[] = languages
    .flatMap(lang => i18nFiles.map(i18nFile => ({lang, i18nFile})));

console.log(langFilePairs)

const loaders = langFilePairs.map(({lang, i18nFile}) => (
    {
        locale: lang,
        key: i18nFile,
        loader: async () => (
            await import((`$lib/assets/lang/${lang}/${i18nFile}.json`))
        ).default
    }
));

/** @type {import('sveltekit-i18n').Config} */
const config = ({
    loaders: loaders
});

export const {
    t,
    locale,
    locales, loading,
    loadTranslations} = new i18n(config);