# Usar el tema personalizado de Material 3 en XML Views

Para utilizar los colores definidos en el tema de Material 3 desde XML, se pueden referenciar sus atributos con `?attr/`.

## Colores principales

Algunos atributos habituales son:

- `?attr/colorPrimary`: color principal.
- `?attr/colorOnPrimary`: color del contenido sobre el principal.
- `?attr/colorSecondary`: color secundario.
- `?attr/colorTertiary`: color terciario.
- `?attr/colorSurface`: color de las superficies.
- `?attr/colorOnSurface`: color del contenido sobre las superficies.
- `?attr/colorError`: color para errores.

## Ejemplo: botón

```xml
<Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Aceptar"
    android:backgroundTint="?attr/colorPrimary"
    android:textColor="?attr/colorOnPrimary" />
```

## Ejemplo: fondo y texto

```xml
<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Hola"
    android:background="?attr/colorSurface"
    android:textColor="?attr/colorOnSurface" />
```

## Colores personalizados adicionales

Si has creado un color propio en `colors.xml`, puedes usarlo directamente:

```xml
android:backgroundTint="@color/mi_color"
```

También puedes exponerlo como atributo del tema en `themes.xml`:

```xml
<item name="miColor">@color/mi_color</item>
```

Y referenciarlo así:

```xml
android:backgroundTint="?attr/miColor"
```

**Consejo:** utiliza `?attr/colorPrimary`, `?attr/colorSurface`, etc. en lugar de escribir códigos hexadecimales. Así los componentes utilizan los colores asignados por tu tema y se adaptan cuando cambias la paleta.
