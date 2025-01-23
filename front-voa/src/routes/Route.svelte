<script>
	import { Router, Route } from "svelte-routing";
	import Main from "../issue/Main.svelte";
	import Login from "../login/src/Login.svelte";
	import 'flowbite/dist/flowbite.css'
    import MainProfile from "../profile/src/MainProfile.svelte";
</script>

<Router>
	<Route path="/main" component={Main} />
	<Route path="/login" component={Login} />
	<Route path="/profile" component={MainProfile} />
</Router>