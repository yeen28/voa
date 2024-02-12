<script>
	import { Label, Input, Checkbox, Button } from 'flowbite-svelte';

	let loginInput = {
		email: '',
		password: ''
	}
	let loginForm;

	function login() {
		// TODO 추후 제거 필요
		loginInput.email = "admin@email.com";
		loginInput.password = "123";

		if (loginInput.email === '' || loginInput.password === '') {
			alert('이메일 또는 비밀번호를 입력해 주세요.');
			return;
		}

		loginForm.submit();
	}
</script>

<section>
	<div class="form-wrap">
		<div class="mb-6">
			<Label for="email" class="mb-2">Email</Label>
			<Input type="email" id="email" placeholder="voa@voa.com" required="" bind:value={loginInput.email} />
		</div>
		<div class="mb-6">
			<Label for="password" class="mb-2">Password</Label>
			<Input type="password" id="password" required="" bind:value={loginInput.password} />
		</div>
		<Checkbox class="mb-6">Remember me</Checkbox>
		<Button color="blue" type="submit" on:click={login}>Login</Button>
	</div>

	<form bind:this={loginForm} method="post" action="/login/user">
		<input type="hidden" name="email" bind:value={loginInput.email}>
		<input type="hidden" name="password" bind:value={loginInput.password}>
	</form>
</section>

<style>
    section {
        width: 100%;
    }

    .form-wrap {
        width: 45vh;
        margin: auto;
    }
</style>