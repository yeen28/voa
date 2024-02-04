<script>
	import { Router, Route } from "svelte-routing";
	import Main from "../main/Main.svelte";
	import Login from "../login/src/Login.svelte";
</script>

<Router>
	<Route path="/main" component={Main} />
	<Route path="/login" component={Login} />
</Router>