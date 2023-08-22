import { sveltekit } from '@sveltejs/kit/vite';
import {viteStaticCopy} from "vite-plugin-static-copy";

/** @type {import('vite').UserConfig} */
const config = {
	server: {
		proxy: {
			'/api': 'http://localhost:8080'
		}
	},
	plugins: [
		sveltekit(),
		viteStaticCopy({
			targets: [
				{
					src: 'src/lib/assets/styles/themes',
					dest: '_app/immutable/assets'
				}
			]
		})
	]
};

export default config;
