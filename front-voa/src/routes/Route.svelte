<script>
	import { Router, Route } from "svelte-routing";
	import Main from "../issue/Main.svelte";
	import Login from "../login/src/Login.svelte";
	import 'flowbite/dist/flowbite.css'
    import Settings from "../profile/src/Settings.svelte";
</script>

<Router>
	<Route path="/main" component={Main} />
	<Route path="/login" component={Login} />
	<Route path="/profile" component={Settings} />
</Router>