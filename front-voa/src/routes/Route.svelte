<script>
	import { Router, Route } from "svelte-routing";
	import Main from "../issue/Main.svelte";
	import Login from "../login/src/Login.svelte";
	import 'flowbite/dist/flowbite.css'
</script>

<Router>
	<Route path="/main" component={Main} />
	<Route path="/login" component={Login} />
</Router>

<style>
	:global(body) {
		height: 100vh;
		display: flex;
		align-items: center;
		justify-content: center;
		margin: 0;
	}
</style>