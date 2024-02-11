import svelte from 'rollup-plugin-svelte';
import commonjs from '@rollup/plugin-commonjs';
import resolve from '@rollup/plugin-node-resolve';
import livereload from 'rollup-plugin-livereload';
import css from 'rollup-plugin-css-only';

const production = !process.env.ROLLUP_WATCH;

const entries = [
	'main'
];

export default entries.map( entryName =>
{
	return {
		input: 'src/main.js',
		output: {
			sourcemap: true,
			format: 'iife',
			name: 'app',
			// TODO 추후 svelte 빌드 결과물을 springboot 실행 시 자동 빌드를 위해 springboot의 static 디렉토리로 위치시킴.
			// file: `../src/main/resources/static/dist/${entryName}/bundle.js`
			file: `../src/main/resources/static/dist/bundle.js`
		},
		plugins: [
			commonjs(),
			css({ output: 'bundle.css' }),
			svelte({
				dev: !production,
				css: css => {
					css.write('../src/main/resources/static/dist/bundle.css');
				}
			}),
			resolve({
				browser: true,
				dedupe: ['svelte']
			}),
			!production && livereload('public'),
			production && proxy({
				// API 서버 주소로 변경
				targets: [{src: '/api/.*', dest: 'http://localhost:8080'}]
			})
		],
		watch: {
			clearScreen: false
		}
	}
});