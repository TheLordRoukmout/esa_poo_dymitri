var builder = WebApplication.CreateBuilder(args);

// 👇 AJOUTE cette ligne pour activer MVC
builder.Services.AddControllersWithViews();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();
app.UseAuthorization();

// 👇 Assure-toi que tu as bien cette ligne
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Cars}/{action=AllCars}/{id?}"
);

app.Run();
